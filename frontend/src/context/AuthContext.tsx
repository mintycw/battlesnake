import { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser } from "../lib/api/authService";
import type { ReactNode } from "react";

type AuthContextType = {
	authenticated: boolean;
	user: string | null;
	refreshAuth: () => void;
};

const AuthContext = createContext<AuthContextType>({
	authenticated: false,
	user: null,
	refreshAuth: () => {},
});

export const AuthProvider = ({ children }: { children: ReactNode }) => {
	const [authenticated, setAuthenticated] = useState(false);
	const [user, setUser] = useState<string | null>(null);

	function refreshAuth(): void {
		getCurrentUser()
			.then((username) => {
				if (!username) {
					return;
				}

				setUser(username);
				setAuthenticated(true);
			})
			.catch(() => {
				setUser(null);
				setAuthenticated(false);
			});
	}

	useEffect(() => {
		refreshAuth();
	}, []);

	return (
		<AuthContext.Provider value={{ authenticated, user, refreshAuth }}>
			{children}
		</AuthContext.Provider>
	);
};

export const useAuth = () => useContext(AuthContext);
