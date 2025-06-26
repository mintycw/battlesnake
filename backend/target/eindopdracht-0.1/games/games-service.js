export default class GamesService {
    async getGameIds() {
        try {
            const response = await fetch('/eindopdracht_war/restservices/history/games', {
                method: "GET",
                credentials: "include"
            });

            if (response.status === 403) {
                alert("Unauthorized")
                throw new Error("You are not authorized to delete this game (403 Forbidden).");
            }

            if (!response.ok) {
                throw new Error(`Failed to fetch game IDs: ${response.status} ${response.statusText}`);
            }

            const gameIds = await response.json();
            return gameIds.map(dto => dto.gameId);
        } catch (error) {
            console.error('Error fetching game IDs:', error);
            return [];
        }
    }

    async getReplay(gameId) {
        try {
            const response = await fetch(`/eindopdracht_war/restservices/history/games/${gameId}`, {
                method: "GET",
                credentials: "include"
            });

            if (response.status === 403) {
                alert("Unauthorized")
                throw new Error("You are not authorized to delete this game (403 Forbidden).");
            }

            if (!response.ok) {
                throw new Error(`Failed to fetch game ID: ${response.status} ${response.statusText}`);
            }
            return await response.json()
        } catch (error) {
            console.error('Error fetching game ID', error);
            return [];
        }
    }

    async removeReplay(gameId) {
        try {
            const response = await fetch(`/eindopdracht_war/restservices/history/games/${gameId}`, {
                method: "DELETE",
                credentials: "include"
            });

            if (response.status === 403) {
                alert("Unauthorized")
                throw new Error("You are not authorized to delete this game (403 Forbidden).");
            }

            if (!response.ok) {
                throw new Error(`Failed to delete game ID: ${response.status} ${response.statusText}`);
            }
            return await response.json()
        } catch (error) {
            console.error('Error deleting game ID:', error);
            return [];
        }
    }
}
