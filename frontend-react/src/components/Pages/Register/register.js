import React from 'react';
import './register.css';
import MinimalHeader from '../../Global/minimalHeader';
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import classNames from "classnames";

function Registrar() {
    let inputClasses = classNames('row-margin');

    return (
        <div className="bodyValue">
            <MinimalHeader/>
            <Container className="containerCenter">
                <div className="flutuante">
                    <div className="cadastro-box">
                        <h1>Registrar</h1>
                        <Form>
                            <Row className="row-margin">
                                <Col>
                                    <div className="textboxCad">
                                        <p>Nome</p>
                                        <input type="text" name="nome" required/>
                                    </div>
                                </Col>
                                <Col>
                                    <div className="textboxCad">
                                        <p>CPF</p>
                                        <input type="text" name="cpf" required/>
                                    </div>
                                </Col>
                            </Row>
                            <Row className="row-margin">
                                <Col>
                                    <div className="textboxCad">
                                        <p>Email</p>
                                        <input type="email" name="email" required/>
                                    </div>
                                </Col>
                                <Col>
                                    <div className="textboxCad">
                                        <p>Senha</p>
                                        <input type="password" name="senha" required/>
                                    </div>
                                </Col>
                            </Row>
                            <Row className="row-margin">
                                <Col>
                                    <div className="textboxCad">
                                        <p>Nascimento</p>
                                        <input type="date" name="data" required/>
                                    </div>
                                </Col>
                                <Col>
                                    <div className="textboxCad">
                                        <p>Telefone</p>
                                        <input type="text" name="telefone" required/>
                                    </div>
                                </Col>
                            </Row>
                            <Row className={inputClasses}>
                                    <div className="textboxCad">
                                        <p>Renda</p>
                                        <input type="number" name="renda" required/>
                                    </div>
                            </Row>
                            <Row className="row-margin">
                                <Button className="btnCad" type="submit">Registrar</Button>
                            </Row>
                        </Form>
                    </div>
                </div>
            </Container>
        </div>
    );
}

export default Registrar;
