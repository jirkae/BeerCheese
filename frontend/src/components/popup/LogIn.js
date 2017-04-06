import React from 'react';
import { Modal, ModalBody, Button } from 'reactstrap';
import { connect } from 'react-redux';
import { login, logout } from '../../actions/auth';

const loginModal = props => {
  // console.log(props);

  const handleAdminLogin = event => {
    // this.preventDefault();
    props.login({
      username: 'admin',
      password: 'admin'
    });
  };

  return (
    <Modal isOpen={true} toggle={props.hideModals}>
      <ModalBody>
        <Button onClick={handleAdminLogin}>Login as Admin</Button>
        {props.auth.isAuthenticated &&
          <Button onClick={props.logout}>Logout</Button>}
        {props.auth.isFetching && <p>Loading</p>}
        {props.auth.isAuthenticated
          ? <p>Authenticated</p>
          : <p>Not authenticated</p>}
        {props.auth.err && <p color="warning">Err: {props.auth.err}</p>}
      </ModalBody>
    </Modal>
  );
};

const mapStateToProps = state => ({
  auth: state.auth
});

export default connect(mapStateToProps, { login, logout })(loginModal);
