import React from 'react';
import { Row, Col, Jumbotron, Button } from 'reactstrap';
import ProductList from '../components/product/ProductList';

const HomePage = () => (
  <Row>
    <Col>
      <Jumbotron>
        <h1 className="display-3">Welcome v Pivních Suvenýrech!</h1>
        <p className="lead">
          <Button outline color="primary">Good bye</Button>
        </p>
        <ProductList />
      </Jumbotron>
    </Col>
  </Row>
  );

export default HomePage;
