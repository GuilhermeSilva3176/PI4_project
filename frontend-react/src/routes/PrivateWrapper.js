import axios from "axios";
import {useEffect, useState} from "react";
import {Navigate, Outlet} from "react-router-dom";
import {TOKEN_EXPIRATION_REF, USER_TOKEN_REF} from "../constants/constants";

const PrivateWrapper = () => {
    const isAuthenticated = localStorage.getItem(USER_TOKEN_REF) !== null &&
        localStorage.getItem(TOKEN_EXPIRATION_REF) > Date.now();

    return isAuthenticated ? <Outlet /> : <Navigate to="/login" />
};

export default PrivateWrapper;