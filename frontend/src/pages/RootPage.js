import * as React from 'react';
import { Container } from 'reactstrap';
import NavBar from '../components/navigation/NavBar';
import NavPanel from '../components/navigation/NavPanel';
import Footer from '../components/navigation/Footer';
import Modals from '../components/navigation/Modals';
import { css } from 'glamor';

const fullWidth = css({
  'margin': 0,
  'padding': 0,
  'width': '100%'
});

const RootPage = ({ children }) => {
  return (
    <div>
      <Container className={`${fullWidth}`}>
        <NavBar />
        <NavPanel />
        <Modals />
          { children }
      </Container>
      <Footer />
    </div>
  );
};

export default RootPage;
