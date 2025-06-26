import type { TGameDetails, TGameHistory } from "../types/historyTypes";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";

export async function getGameHistory(): Promise<TGameHistory[]> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/history/games`, {
			method: "GET",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(
							error.message || "Failed to fetch game history",
						);
					});
				}
				return response.json();
			})
			.then((data) => {
				resolve(data);
			})
			.catch((error) => {
				reject(error);
			});
	});
}

export async function getGameDetails(gameId: string): Promise<TGameDetails> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/history/games/${gameId}`, {
			method: "GET",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(
							error.message ||
								`Failed to fetch game details for ${gameId}`,
						);
					});
				}
				return response.json();
			})
			.then((data) => {
				resolve(data);
			})
			.catch((error) => {
				reject(error);
			});
	});
}

export async function deleteGameFromHistory(gameId: string): Promise<boolean> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/history/games/${gameId}`, {
			method: "DELETE",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
			},
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(
							error.message ||
								`Failed to delete game ${gameId} from history`,
						);
					});
				}
				resolve(true);
			})
			.catch((error) => {
				reject(error);
			});
	});
}
