import React from 'react';
import {
  Modal, ModalBody, Container, Row, Form, FormGroup,
  Label, Input, Button, Col, InputGroupAddon, InputGroup,
} from 'reactstrap';

import api from '../../api';

export default class NewSupplierAdmin extends React.Component {

  onSubmit = (event) => {
    event.preventDefault();
    api.post('suppliers', new FormData(event.target))
      .then(data => {
        console.log('success ', data);
      })
      .catch(response => {
        console.log('error ', response);
      });
  };

  render() {
    return (
      <Modal isOpen={true} toggle={this.props.hideModals}>
        <ModalBody>
          <Container>
            <Row>
              <h3>Přidat nového dodavatele</h3>
              <br/> <br/>
              <Form onSubmit={this.onSubmit}>
                <FormGroup row>
                  <Label for="name" sm={3}>Jméno</Label>
                  <Col sm={9}>
                    <Input required type="text" name="name" id="name"/>
                  </Col>
                </FormGroup>

                <FormGroup row>
                  <Label for="phoneNumber" sm={3}>Telefon</Label>
                  <Col sm={9}>
                    <Input required type="number" name="phoneNumber" id="phoneNumber"/>
                  </Col>
                </FormGroup>

                <FormGroup row>
                  <Label for="deliveryTime" sm={3}>Dodací lhůta</Label>
                  <Col sm={9}>
                    <InputGroup>
                      <Input required type="number" name="deliveryTime" id="deliveryTime"/>
                      <InputGroupAddon>Dny</InputGroupAddon>
                    </InputGroup>
                  </Col>
                </FormGroup>

                <FormGroup check row>
                  <Col sm={{size: 10, offset: 2}}>
                    <Button type="submit">Vytvořit</Button>
                  </Col>
                </FormGroup>
              </Form>
            </Row>
          </Container>
        </ModalBody>
      </Modal>
    );
  }
};
