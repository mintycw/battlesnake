const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:8080";

export function login(username: string, password: string): Promise<string> {
	return new Promise((resolve, reject) => {
		if (!username || !password) {
			reject(new Error("Username and password are required"));
			return;
		}

		fetch(`${BACKEND_URL}/api/auth/login`, {
			method: "POST",
			credentials: "include",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({ username, password }),
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(error.message || "Login failed");
					});
				}
				return response.json();
			})
			.then((token) => {
				if (!token) {
					throw new Error("No token received");
				}
				resolve(token);
			});
	});
}

export function logout(): Promise<void> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/auth/logout`, {
			method: "POST",
			credentials: "include",
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(error.message || "Logout failed");
					});
				}
				resolve();
			})
			.catch((error) => {
				reject(error);
			});
	});
}

export function getCurrentUser(): Promise<string | null> {
	return new Promise((resolve, reject) => {
		fetch(`${BACKEND_URL}/api/auth/me`, {
			method: "GET",
			credentials: "include",
		})
			.then((response) => {
				if (!response.ok) {
					return response.json().then((error) => {
						throw new Error(
							error.message || "Failed to fetch current user",
						);
					});
				}
				return response.json();
			})
			.then((data) => {
				resolve(data.username || null);
			})
			.catch((error) => {
				reject(error);
			});
	});
}
