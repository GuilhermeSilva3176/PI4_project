import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Global/header';
import Home from './components/Pages/Home/home';
import Finances from './components/Pages/Finances/homeFinances';
import Investimentos from './components/Pages/Investimentos/investimentos';
import Login from './components/Pages/Login/login';
import './App.css';
import Register from "./components/Pages/Register/register";

function App() {
  return (
    <Router>
      <div>
        <Header/>
        <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/financas" element={<Finances/>}/>
          <Route path="/investimentos" element={<Investimentos/>}/>
          <Route path="/login" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;
