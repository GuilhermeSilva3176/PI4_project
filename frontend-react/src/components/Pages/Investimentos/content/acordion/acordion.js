import React from "react";
import './acordion.css';

function Accordion({ handleVerDetalhesClick }) {
  return (
      <ul className="accordion">
        <h2 className='title'>Minha Carteira</h2>
        <li className='val1'>
          <input type="radio" name="accordion" id="acoes" defaultChecked />
          <label className="acLabels" htmlFor="acoes">AÇÕES</label>
          <div className="content">
            <div className="content-body">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
            </div>
            <div className="content-footer">
              <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
            </div>
          </div>
        </li>
        <li className='val2'>
          <input type="radio" name="accordion" id="fiis" />
          <label className="acLabels" htmlFor="fiis">FIIS</label>
          <div className="content">
            <div className="content-body">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
            </div>
            <div className="content-footer">
              <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
            </div>
          </div>
        </li>
        <li className='val3'>
          <input type="radio" name="accordion" id="cdb" />
          <label className="acLabels" htmlFor="cdb">RENDA FIXA</label>
          <div className="content">
            <div className="content-body">
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
              <p>
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
              </p>
            </div>
            <div className="content-footer">
              <button className='btnVerDetalhes' onClick={handleVerDetalhesClick}>Detalhes</button>
            </div>
          </div>
        </li>
      </ul>
  );
}

export default Accordion;