import axios from "axios";
import {useEffect, useState} from "react";

function useAuth() {
    const [user, setUser] = useState(null);
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            axios.get('/api/auth/me', {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }).then(response => {
                setUser(response.data);
            }).catch(() => {
                localStorage.removeItem('token');
                setUser(null);
            });
        }
    }, []);
    return { user, setUser };
}

const PrivateRoutes = ({ children }) => {
    const { user } = useAuth();
    return user ? children : <Redirect to="/login" />;
}