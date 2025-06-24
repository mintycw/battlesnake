export default class LoginService {
    isLoggedIn() {
        // Will rely on /me to confirm if token is valid
        return this.getUser().then(user => !!user);
    }

    login(user, password) {
        return fetch("/eindopdracht_war/restservices/auth/login", {
            method: "POST",
            credentials: 'include',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username: user, password })
        })
            .then(response => {
                if (!response.ok) throw new Error("Login failed");
                return response.json();
            })
            .then(data => {
                console.log("Login successful:", data);
                return true;
            });
    }

    getUser() {
        return fetch("/eindopdracht_war/restservices/auth/me", {
            method: "GET",
            credentials: 'include',
        })
            .then(response => {
                if (!response.ok) return null;
                return response.json();
            });
    }

    logout() {
        return fetch("/eindopdracht_war/restservices/auth/logout", {
            method: "POST",
            credentials: 'include',
        }).catch(() => {})
            .finally(() => Promise.resolve());
    }
}