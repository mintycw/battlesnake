import { createContext, useContext, useEffect, useState } from "react";
import { getCurrentUser } from "../lib/api/authService";
import type { ReactNode } from "react";
import { useNavigate } from "react-router-dom";

type AuthContextType = {
	authenticated: boolean;
	user: string | null;
	roles: string[] | null;
	refreshAuth: () => void;
};

const AuthContext = createContext<AuthContextType>({
	authenticated: false,
	user: null,
	roles: null,
	refreshAuth: () => {},
});

export const AuthProvider = ({ children }: { children: ReactNode }) => {
	const [authenticated, setAuthenticated] = useState(false);
	const [user, setUser] = useState<string | null>(null);
	const [roles, setRoles] = useState<string[] | null>(null);

	const navigate = useNavigate();

	function refreshAuth(): void {
		getCurrentUser()
			.then((data) => {
				if (!data) {
					setUser(null);
					setAuthenticated(false);
					navigate("/");
					return;
				}
				setUser(data.username);
				setRoles(data.roles || []);
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
		<AuthContext.Provider
			value={{ authenticated, user, roles, refreshAuth }}
		>
			{children}
		</AuthContext.Provider>
	);
};

export const useAuth = () => useContext(AuthContext);
