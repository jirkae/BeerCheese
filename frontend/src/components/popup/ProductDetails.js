import React from 'react';
import { Modal, ModalBody, Container, Row, Col } from 'reactstrap';

export default (props) => {
  return(
    <Modal isOpen={true} toggle={props.hideModals}>
      <ModalBody>
        <Container>
          <Row>
            <Col>
              <Row>
                {props.data.name}
              </Row>
              <Row>
                <img src="https://placeholdit.imgix.net/~text?txtsize=33&txt=318%C3%97180&w=318&h=180" alt="Card cap" />
              </Row>
              <Row>
                {props.data.price}
              </Row>
            </Col>
            <Col>
              {props.data.description}
            </Col>
          </Row>
        </Container>
      </ModalBody>
    </Modal>
  );
}