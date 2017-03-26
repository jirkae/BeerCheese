import React from 'react';
import { Modal, ModalBody, Container, Row } from 'reactstrap';

export default props => {
  console.log(props);
  return (
    <Modal isOpen={true} toggle={props.hideModals}>
      <ModalBody>
        <Container>
          <Row>
            create new product
          </Row>
        </Container>
      </ModalBody>
    </Modal>
  );
};