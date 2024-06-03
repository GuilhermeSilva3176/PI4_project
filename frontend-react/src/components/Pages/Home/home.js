import React from "react";
import Hero from './Content/hero';
import Rows from './Content/rows';
import Footer from '../../Global/footer';
import './home.css';

function Home() {
    return (
        <div id="home">
            <Hero/>
            <Rows/>
            <Footer/>
        </div>
    );
}

export default Home;