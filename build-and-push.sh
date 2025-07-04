#!/bin/bash
set -euo pipefail

# Set your DockerHub username
DOCKER_USERNAME="mintycw"

# Load environment variables from .env
if [ -f .env.production ]; then
    export $(grep -v '^#' .env.production | xargs)
else
    echo ".env.production file not found!"
    exit 1
fi

echo "Building and pushing battlesnake images..."

# Ensure buildx is set up
docker buildx create --use --name battlesnake-builder || docker buildx use battlesnake-builder

# Build and push backend
docker buildx build --platform linux/amd64 \
  --build-arg MONGO_URI="$MONGO_URI" \
  --build-arg SECRET_KEY="$SECRET_KEY" \
  --build-arg FRONTEND_URL="$FRONTEND_URL" \
  -t $DOCKER_USERNAME/battlesnake-backend:latest ./backend \
  --push

# Build and push frontend
docker buildx build --platform linux/amd64 \
  --build-arg VITE_BACKEND_URL="$VITE_BACKEND_URL" \
  -t $DOCKER_USERNAME/battlesnake-frontend:latest ./frontend \
  --push

echo "✅ Images pushed successfully!"