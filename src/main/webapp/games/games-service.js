export default class GamesService {
    async getGameIds() {
        try {
            const response = await fetch('/eindopdracht_war/restservices/history/games');
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
            const response = await fetch(`/eindopdracht_war/restservices/history/games/${gameId}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch game ID: ${response.status} ${response.statusText}`);
            }
            return await response.json()
        } catch (error) {
            console.error('Error fetching game IDs:', error);
            return [];
        }
    }

    async removeReplay(gameId) {
        //TODO: gebruik fetch om een enkele game (bij de server) te deleten
        return Promise.resolve();
    }
}
