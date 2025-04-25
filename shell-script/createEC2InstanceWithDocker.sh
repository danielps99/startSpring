#!/bin/bash

# Variables
KEY_NAME="start-spring-key"
SECURITY_GROUP_NAME="start-spring-sg"
INSTANCE_NAME="start-spring-docker-instance"
REGION="us-east-1"
AMI_ID="ami-0c02fb55956c7d316"  # Amazon Linux 2 (official)
INSTANCE_TYPE="t2.micro"

# Create a key pair (only if it doesn't exist)
if ! aws ec2 describe-key-pairs --key-names "$KEY_NAME" --region "$REGION" >/dev/null 2>&1; then
  echo "Creating key pair..."
  aws ec2 create-key-pair --key-name "$KEY_NAME" --query 'KeyMaterial' --output text > "${KEY_NAME}.pem"
  chmod 400 "${KEY_NAME}.pem"
else
  echo "Key pair already exists."
fi

# veriry and create if group not exist
SG_ID=$(aws ec2 describe-security-groups \
  --group-names "$SECURITY_GROUP_NAME" \
  --region "$REGION" \
  --query 'SecurityGroups[0].GroupId' \
  --output text 2>/dev/null)

if [ -z "$SG_ID" ]; then
  SG_ID=$(aws ec2 create-security-group \
    --group-name "$SECURITY_GROUP_NAME" \
    --description "Allow SSH and HTTP" \
    --region "$REGION" \
    --query 'GroupId' \
    --output text)
  
  # Authorize inbound rules
  aws ec2 authorize-security-group-ingress --group-id "$SG_ID" \
    --protocol tcp --port 22 --cidr 0.0.0.0/0

  aws ec2 authorize-security-group-ingress --group-id "$SG_ID" \
    --protocol tcp --port 80 --cidr 0.0.0.0/0
else
  echo "Security group $SECURITY_GROUP_NAME already exists with ID $SG_ID"
fi

# User data script to install Docker and add user to docker group
USER_DATA=$(cat <<EOF
#!/bin/bash
yum update -y
amazon-linux-extras install docker -y
service docker start
usermod -aG docker ec2-user
systemctl enable docker
docker network create springnet
docker run -d --name start-spring-db --network springnet -e MYSQL_DATABASE=startSpringDb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_USER=developer -e MYSQL_PASSWORD=freeaccess mysql:8.0

EOF
)

# Launch EC2 instance with Docker installation via user data
INSTANCE_ID=$(aws ec2 run-instances \
  --image-id "$AMI_ID" \
  --instance-type "$INSTANCE_TYPE" \
  --key-name "$KEY_NAME" \
  --security-group-ids "$SG_ID" \
  --region "$REGION" \
  --user-data "$USER_DATA" \
  --query 'Instances[0].InstanceId' \
  --output text)


if [ -z "$INSTANCE_ID" ]; then
  echo "Error: EC2 instance was not created. Exiting script."
  exit 1
fi

# Tag the instance
aws ec2 create-tags --resources "$INSTANCE_ID" --tags Key=Name,Value="$INSTANCE_NAME" --region "$REGION"

# Wait for the instance to start and fetch public IP
echo "Waiting for instance to be in running state..."
aws ec2 wait instance-running --instance-ids "$INSTANCE_ID" --region "$REGION"

PUBLIC_IP=$(aws ec2 describe-instances \
  --instance-ids "$INSTANCE_ID" \
  --region "$REGION" \
  --query 'Reservations[0].Instances[0].PublicIpAddress' \
  --output text)

echo "Instance is running!"
echo "Connect with: ssh -i ${KEY_NAME}.pem ec2-user@${PUBLIC_IP}"
