import React, { Component } from 'react';
import { Container, Row, Col } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';
import PriceCalculation from '../../components/packageOverview/PriceCalculation';

export default class PackageOverviewRootPage extends Component {
    render() {
        return (
            <Container>
                <PackageOverviewNav />
                <Row>
                    <Col md={8}>
                        {this.props.children}
                    </Col>
                    <Col md={4}>
                        <PriceCalculation />
                    </Col>
                </Row>
            </Container>
        );
    }
}