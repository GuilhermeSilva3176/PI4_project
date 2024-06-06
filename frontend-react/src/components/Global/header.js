import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import './header.css';
import axios from "axios";
import { TOKEN_EXPIRATION_REF, USER_TOKEN_REF } from "../../constants/constants";

function Header() {
  let navigate = useNavigate();
  const location = useLocation();
  const isAuthenticated = localStorage.getItem(USER_TOKEN_REF) !== null &&
    localStorage.getItem(TOKEN_EXPIRATION_REF) > Date.now();

  const [isCollapsed, setIsCollapsed] = useState(true);

  const handleLogout = () => {
    if (!window.confirm('Deseja realmente sair?')) return;

    axios.post('http://localhost:8080/api/v1/auth/logout', {}, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem(USER_TOKEN_REF)
      }
    }).then((response) => {
      if (response.status === 200) {
        localStorage.removeItem('user-token');
        localStorage.removeItem('token-expiry-date');
        localStorage.removeItem('user-name');

        alert('Logout efetuado com sucesso');

        navigate("/");
      }
    }).catch((error) => {
      alert('Erro ao fazer logout ' + error);
    });
  };

  const changeHomeRoute = () => {
    return isAuthenticated ? '/home' : '/';
  };

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth >= 992) {
        setIsCollapsed(true);
      }
    };

    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return (
    <header id="headerChart">
      <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-dark justify-content-between fixed-top">
          <Link className="navbar-logo" to={changeHomeRoute()}>
            <img src='/images/header_logo.svg' alt='Header Logo' className='img-fluid' />
          </Link>
          <button
            className={`navbar-toggler ${isCollapsed ? 'collapsed' : 'expanded'}`}
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded={!isCollapsed}
            aria-label="Toggle navigation"
            onClick={() => setIsCollapsed(!isCollapsed)}>
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className={`collapse navbar-collapse ${isCollapsed ? '' : 'show'}`} id="navbarNav">
            <ul className="navbar-nav ms-auto mb-lg-0">
              <li className="nav-item">
                <Link className={`nav-link ${location.pathname === '/home' ? 'active' : ''}`} to={changeHomeRoute()}>Home</Link>
              </li>
              <li className="nav-item">
                <Link className={`nav-link ${location.pathname === '/financas' ? 'active' : ''}`} to="/financas">Finanças</Link>
              </li>
              <li className="nav-item">
                <Link className={`nav-link ${location.pathname === '/investimentos' ? 'active' : ''}`} to="/investimentos">Carteira</Link>
              </li>
              <li>
                <div className={`nav-line ${isCollapsed ? '' : 'd-none'}`}></div>
              </li>
              <li className="nav-item">
                {isAuthenticated ?
                  <a className="nav-link" onClick={handleLogout}>Logout</a> :
                  <Link className={`nav-link ${location.pathname === '/login' ? 'active' : ''}`} to="/login">Login</Link>
                }
              </li>
            </ul>
          </div>
        </nav>
      </div>
    </header>
  );
}

export default Header;
