import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import './header.css';
import axios from "axios";
import {TOKEN_EXPIRATION_REF, USER_TOKEN_REF} from "../../constants/constants";

function Header(){
  let navigate = useNavigate()
  const isAuthenticated = localStorage.getItem(USER_TOKEN_REF) !== null &&
      localStorage.getItem(TOKEN_EXPIRATION_REF) > Date.now();

  const handleLogout = () => {
    if (!window.confirm('Deseja realmente sair?')) return

    axios.post('http://localhost:8080/api/v1/auth/logout', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem(USER_TOKEN_REF)
        }
    }).then((response) => {
      if (response.status === 200) {
        localStorage.removeItem('user-token')
        localStorage.removeItem('token-expiry-date')
        localStorage.removeItem('user-name')

        alert('Logout efetuado com sucesso')

        navigate("/")
      }
    }).catch((error) => {
      alert('Erro ao fazer logout ' + error)
    })
  }


  return (
    <header id="headerChart">
      <div className="container-fluid">
        <nav className='navbar navbar-expand-lg navbar-dark justify-content-between fixed-top'>

          <Link className='navbar-logo' to='/'>
            <img src='/images/header_logo.svg' alt='Header Logo' className='img-fluid'/>
          </Link>

          <nav className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto mb-lg-0">
              <li className="nav-item">
                <Link className="nav-link" to="/">Home</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/financas">Finanças</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to="/investimentos">Carteira</Link>
              </li>
              <li className="nav-item">
                {isAuthenticated ?
                  <a className="nav-link" onClick={handleLogout}>Logout</a> :
                  <Link className="nav-link" to="/login">Login</Link>
                }
              </li>
            </ul>
          </nav>
        </nav>
      </div>
    </header>
  )
}

export default Header;
