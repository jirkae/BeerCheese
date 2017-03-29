import React, { Component } from 'react';
import { Container } from 'reactstrap';
import PackageOverviewNav from '../../components/navigation/PackageOverviewNav';

export default class PackageOverviewRootPage extends Component {
    render() {
        return(
            <Container>
                <PackageOverviewNav />
                {this.props.children}
            </Container>
        );
    }
}