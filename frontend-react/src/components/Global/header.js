import React from 'react';
import { Link } from 'react-router-dom';
import './header.css';

function Header(){
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
                <Link className="nav-link" to="/login">Login</Link>
              </li>
            </ul>
          </nav>
        </nav>
      </div>
    </header>
  )
}

export default Header;
