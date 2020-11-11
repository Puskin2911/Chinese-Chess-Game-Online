import React from "react";

const AuthContext = React.createContext(null);

export default function ProvideAuth({children}) {
    const [isAuthenticated, setAuthenticated] = React.useState(false);

    const logout = () => {
        setAuthenticated(false);
    }
    const auth = {
        isAuthenticated,
        setAuthenticated,
        logout
    };
    return (
        <AuthContext.Provider value={auth}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    return React.useContext(AuthContext);
}