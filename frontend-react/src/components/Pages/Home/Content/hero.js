import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './hero.css';

function Hero(){
  return (
    <section id="hero" className="row">
      <div className="text col-md-5 align-self-center">
        <h1>Bem-vindo ao Fit Finance, O seu assistente para controle financeiro.</h1>
        <hr/>
        <p>
          No Fit Finance, estamos comprometidos em ser seu parceiro em todas as etapas do seu percurso
          financeiro.
        </p>
        <Link to="/register">
          <button className="cadastrarBotao">Abra sua conta</button>
        </Link>
      </div>
      <div className="img col-md-4">
        <img src="/images/first_home_image.svg" alt="Presentation" className="img-fluid"/>
      </div>
    </section>
  );
}
 
export default Hero;