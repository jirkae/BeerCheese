import React, { Component } from 'react';
import {
  Navbar,
  NavbarToggler,
  Collapse,
  Nav,
  NavItem,
  NavLink,
  NavbarBrand
} from 'reactstrap';
import { Link } from 'react-router';
import localizedTexts from '../../text_localization/LocalizedStrings';
import { connect } from 'react-redux';
import { openModal } from '../../actions/openModal';
import { css } from 'glamor';
import { logout } from '../../actions/auth';

const BEER_IMG_URL = 'https://img.clipartfox.com/c6c3f93fcfdd38440d093b3140604408_beer-free-to-use-clipart-beer-clipart-transparent-background_985-1280.png';

const AuthNav = ({ isAuth, children }) => {
  return isAuth ? <div>{children}</div> : null;
};

class NavBar extends Component {
  state = {
    isOpen: false
  };

  toggle = () => {
    this.setState({
      isOpen: !this.state.isOpen
    });
  };

  render() {
    const { auth } = this.props;
    return (
      <Navbar color="faded" light toggleable>
        <NavbarBrand tag={Link} to="/">
          <img
            src={BEER_IMG_URL}
            alt="beerIcon"
            className={`${this.cssBeerIcon}`}
          />
          <span>
            &nbsp;
            Pivní suvenýry
          </span>
        </NavbarBrand>
        <NavbarToggler right onClick={this.toggle} />
        <Collapse isOpen={this.state.isOpen} navbar>
          <Nav className="ml-auto" navbar>
            <NavItem>
              <NavLink tag={Link} to="/about_us">
                {localizedTexts.NavBar.aboutUs}
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/conditions">
                {localizedTexts.NavBar.terms}
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/contact">
                {localizedTexts.NavBar.contact}
              </NavLink>
            </NavItem>
            <NavItem>
              <NavLink tag={Link} to="/">
                {localizedTexts.NavBar.mainPage}
              </NavLink>
            </NavItem>
            <NavItem tag={AuthNav} isAuth={auth.isAuthenticated}>
              <NavLink tag={Link} to="/package-overview">
                {localizedTexts.NavBar.packagesOverview}
              </NavLink>
            </NavItem>
            <NavItem tag={AuthNav} isAuth={auth.isAuthenticated}>
              <NavLink tag={Link} to="#" onClick={() => this.props.logout()}>
                {localizedTexts.NavBar.logOut}
              </NavLink>
            </NavItem>
            <NavItem tag={AuthNav} isAuth={!auth.isAuthenticated}>
              <NavLink
                tag={Link}
                to="#"
                onClick={() =>
                  this.props.openModal({ name: 'logIn', data: null })}
              >
                {localizedTexts.NavBar.logIn}
              </NavLink>
            </NavItem>
            <NavItem tag={AuthNav} isAuth={!auth.isAuthenticated}>
              <NavLink tag={Link} to="/register">
                {localizedTexts.NavBar.signUp}
              </NavLink>
            </NavItem>
          </Nav>
        </Collapse>
      </Navbar>
    );
  }

  cssBeerIcon = css({
    height: '50px',
    width: '50px'
  });
}

const mapStateToProps = state => ({
  auth: state.auth
});
export default connect(mapStateToProps, {
  openModal,
  logout
})(NavBar);
