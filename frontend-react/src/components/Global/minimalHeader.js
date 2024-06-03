import React from 'react';
import './header.css';

function MinimalHeader(){
  return (
    <header id="headerChart">
      <div className="container-fluid">
        <nav className='navbar navbar-expand-lg navbar-dark justify-content-between fixed-top'>

          <a className='navbar-logo' href='#'>
            <img src='/images/header_logo.svg' alt='Header Logo' className='img-fluid'/>
          </a>

        </nav>
      </div>
    </header>
  )
}

export default MinimalHeader;