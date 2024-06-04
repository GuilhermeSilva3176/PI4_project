import React, {useState} from "react";
import './login.css';
import MinimalHeader from "../../Global/minimalHeader";
import {Link, useNavigate} from "react-router-dom";
import {Form} from "react-bootstrap";
import axios from "axios";
import InputComponent from "../Register/components/InputComponent";

function Login(){
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    let navigate = useNavigate();
    const goToHome = () => {
        navigate('/financas');
    }

    const handleSubmit = (event) => {
        event.preventDefault()

        const authenticationRequest = {
            email: email,
            password: password
        }

        axios.post('http://localhost:8080/api/v1/auth/authenticate', authenticationRequest)
            .then(response => {
                if (response.status === 200) {
                    localStorage.setItem('user-token', response.data.access_token)
                }
            })
            .then(() => {
                alert('Usuário logado com sucesso!')
                goToHome()

            })
            .catch(error => {
                alert('Erro ao logar usuário: ' + error)
            });
    }

  return(
    <div className="bodyValue">
    <MinimalHeader/>
    <div className="flutuante">
      <div className="login-box">
        <p className="titulo"><span className="underline"></span>Entrar</p>

          <Form onSubmit={handleSubmit}>
              <div className="textbox">
                  <InputComponent label="Email" inputType="tel" value={email}
                                  onChange={(event) => setEmail(event.target.value)}/>
              </div>

              <div className="textbox">
                <InputComponent inputType="password" label="Senha" value={password}
                onChange={(event) => setPassword(event.target.value)}/>
              </div>

              <div className="subscribe">
                  <p>Not signed yet?&nbsp;<Link to="/register">Clique aqui</Link></p>
              </div>
              <button type="submit" className="btn-login">Login</button>
            </Form>
        </div>
    </div>
    </div>
  )
}

export default Login;