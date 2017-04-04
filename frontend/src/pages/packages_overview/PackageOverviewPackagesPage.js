import React, { Component } from 'react';
import { Row, Table, Button } from 'reactstrap';
import localizedTexts from '../../text_localization/LocalizedStrings';

export default class PackageOverviewPackagesPage extends Component {
  render() {
    function makeRow() {
      return (<tr>
        <td>Balíček 1</td>
        <td><input type="number" value="2" /></td>
        <td>750</td>
        <td><Button size="sm" color="secondary">{localizedTexts.PackageOverview.packages.edit}</Button></td>
        <td><Button size="sm" color="secondary">{localizedTexts.PackageOverview.packages.remove}</Button></td>
      </tr>);
    }

    function getRows() {
      let rows = [];
      for (var i = 0; i < 10; i++) {
        rows.push(makeRow());
      }
      return rows;
    }

    return (
      <Table>
        <tbody>
          {getRows()}
        </tbody>
      </Table>
    );
  }
}
