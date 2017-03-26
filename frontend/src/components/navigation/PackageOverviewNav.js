import React from 'react';
import { Link } from 'react-router';
import { Row, Col, Button, NavLink, NavItem, Nav } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';

export default props => (
  <Row>
    <Col xl="2" lg="2" md="2" sm="2" xs="3">
      <Button>{localizedTexts.PackageOverviewNav.back}</Button>
    </Col>
    <Col xl="8s" lg="8" md="8" sm="8" xs="6">
      <Nav>
        <NavItem>
          <NavLink tag={Link} to="/package_overview_packages">
            {localizedTexts.PackageOverviewNav.packages}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/package_overview_del_pay">
            {localizedTexts.PackageOverviewNav.delPay}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/package_overview_del_details">
            {localizedTexts.PackageOverviewNav.delDetails}
          </NavLink>
        </NavItem>
        <NavItem>
          <NavLink tag={Link} to="/package_overview_summary">
            {localizedTexts.PackageOverviewNav.summary}
          </NavLink>
        </NavItem>
      </Nav>
    </Col>
    <Col xl="2" lg="2" md="2" sm="2" xs="3">
      <Button>{localizedTexts.PackageOverviewNav.next}</Button>
    </Col>
  </Row>
);