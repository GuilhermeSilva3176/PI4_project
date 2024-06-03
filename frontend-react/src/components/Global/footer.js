import React from "react";
import './footer.css';

function Footer(){
  return (
    <footer id="footer">
      <h2 className="titleRow" >Sobre nós</h2>
      <hr/>
      <p>
        Fundada em 2024, a Fit Finance é uma empresa preocupada na sua evolução financeira, fornecendo
        recursos essenciais para uma postura construtiva. Nossa missão é fornecer ferramentas acessíveis e
        intuitivas que ajudem a população a melhorar sua qualidade de vida e aprimorar a gestão de seus
        negócios.
      </p>
      <br/>
      <p>
        Nós da Fit Finance fornecemos uma plataforma de finanças inovadora, projetada para capacitar você a
        tomar controle total de suas finanças pessoais e comerciais. Fornecendo ferramentas acessíveis e
        intuitivas que ajudam a população a melhorar sua qualidade de vida e aprimorar a gestão de seus
        negócios.
      </p>
      <br/>
      <p>
        Acreditamos firmemente que uma vida financeira saudável é essencial para o bem-estar geral. Por meio
        da nossa plataforma, buscamos capacitar indivíduos e empreendedores a alcançar seus objetivos
        financeiros, seja economizando para o futuro, eliminando dívidas ou expandindo seus negócios.
      </p>   

      <div id="footer-scrollable">
        <div className="container-fluid">
          <div>
            <p className="text-end">© 2024 Fit Finanças. Todos os direitos reservados.</p>
          </div>
        </div>
      </div>
    </footer>
  );
}

export default Footer;