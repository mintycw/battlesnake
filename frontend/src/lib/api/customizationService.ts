import type {
	TSnakeCustomization,
	TSnakeCustomizationUpdate,
} from "../types/customizationTypes";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";

export async function getSnake(): Promise<TSnakeCustomization> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/snake`, {
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
								`Failed to fetch snake customization`,
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

export async function updateSnake(
	customization: TSnakeCustomizationUpdate,
): Promise<TSnakeCustomizationUpdate> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/snake`, {
			method: "PUT",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(customization),
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(
							error.message ||
								`Failed to update snake customization`,
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
