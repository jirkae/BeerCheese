import * as React from 'react';
import { Container } from 'reactstrap';
import NavBar from '../components/navigation/NavBar';
import LoginRegisterNav from '../components/navigation/LoginRegisterNav';
import Footer from '../components/navigation/Footer';
import Modals from '../components/navigation/Modals'; 

const RootPage = ({ children }) => {
  return (
    <div>
      <Container fluid>
        <LoginRegisterNav />
      </Container>
      <Container fluid style={{background: '#cfcfcf'}}>
        <NavBar />
      </Container>
      <Container fluid>
        <Modals />
        {children}
      </Container>
      <Footer />
    </div>
  );
};

export default RootPage;
