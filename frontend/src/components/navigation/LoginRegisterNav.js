import React, { Component } from 'react';
import {
    Nav,
    Row,
    Col,
    NavItem,
    NavLink,
} from 'reactstrap';
import { Link } from 'react-router';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';
import { logout } from '../../actions/auth';

class LoginRegisterNav extends Component {
    render() {
        return (
            <Row>
                <Col xs={12}>
                    <Nav className="pull-right">
                        <NavItem>
                            <NavLink
                                // tag={Link}
                                // to="#"
                                onClick={() =>
                                    this.props.openModal({ name: 'logIn', data: null })}
                            >
                                {localizedTexts.NavBar.logIn}
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to="/register">
                                {localizedTexts.NavBar.signUp}
                            </NavLink>
                        </NavItem>
                        <NavItem>
                            <NavLink tag={Link} to="#" onClick={() => this.props.logout()}>
                                {localizedTexts.NavBar.logOut}
                            </NavLink>
                        </NavItem>
                    </Nav>
                </Col>
            </Row>
        );
    }
}

export default connect(null, {
    openModal,
    logout
})(LoginRegisterNav);
