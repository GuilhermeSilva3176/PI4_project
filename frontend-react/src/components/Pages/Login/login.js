import React from "react";
import './login.css';
import MinimalHeader from "../../Global/minimalHeader";
import {Link} from "react-router-dom";

function Login(){
  return(
    <div className="bodyValue">
    <MinimalHeader/>
    <div className="flutuante">
      <div className="login-box">
        <p className="titulo"><span className="underline"></span>Entrar</p>

          <form action="home_logged.html">

              <div className="textbox">
                <p>Email</p>
                <input type="email" name="email" required/>
              </div>

              <div className="textbox">
                <p>Senha</p>
                <input type="password" name="senha" required/>
              </div>

              <div className="subscribe">
                  <p>Not signed yet?&nbsp;<Link to="/register">Clique aqui</Link></p>
              </div>
              <input type="submit" className="btn-login" value="  Login  "/>
            </form>
        </div>
    </div>
    </div>
  )
}

export default Login;