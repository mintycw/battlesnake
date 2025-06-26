export default class SnakeService {
    async getSnake() {
        try {
            const response = await fetch('/eindopdracht_war/restservices/snake', {
                method: "GET",
                credentials: "include"
            });

            if (response.status === 403) {
                alert("Unauthorized")
                throw new Error("You are not authorized to fetch this snake (403 Forbidden).");
            }

            if (!response.ok) {
                throw new Error(`Failed to update snake: ${response.status} ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error updating snake:', error);
            return [];
        }
    }

    async updateSnake(updatedSnake) {
        try {
            const response = await fetch('/eindopdracht_war/restservices/snake', {
                method: "PUT",
                credentials: "include",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedSnake)
            });

            if (response.status === 403) {
                alert("Unauthorized")
                throw new Error("You are not authorized to update this snake (403 Forbidden).");
            }

            if (!response.ok) {
                throw new Error(`Failed to update snake: ${response.status} ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Error updating snake:', error);
            return [];
        }
    }
}
