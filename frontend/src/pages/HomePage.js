import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Row, Col, Container, Jumbotron, Button } from 'reactstrap';

export default class HomePage extends Component {
  render() {
    return (
      <Container>
        <Row>
          <Col>
            <Jumbotron>
              <h1 className="display-3">Welcome v Pivních Suvenýrech!</h1>
              <p className="lead">
                <Button outline color="primary">Good bye</Button>
              </p>
            </Jumbotron>

          </Col>
        </Row>
      </Container>
    );
  }
}
