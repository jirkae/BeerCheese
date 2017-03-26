import React from 'react';
import { IndexRoute, Route } from 'react-router';

import HomePage from './pages/HomePage';
import PageNotFound from './pages/PageNotFound';
import RootPage from './pages/RootPage';
import AdminLoginPage from './pages/admin/AdminLogInPage';
import CreatePackageBeerPage from './pages/configurator/CreatePackageBeerPage';
import CreatePackageMessagePage from './pages/configurator/CreatePackageMessagePage';
import CreatePackagePackagePage from './pages/configurator/CreatePackagePackagePage';
import CreatePackageSummaryPage from './pages/configurator/CreatePackageSummaryPage';
import CreatePackageSupplementPage from './pages/configurator/CreatePackageSupplementPage';
import PackageOverviewSummaryPage from './pages/packages_overview/PackageOverviewSummaryPage';
import PackageOverviewPackagesPage from './pages/packages_overview/PackageOverviewPackagesPage';
import PackageOverviewDelPayPage from './pages/packages_overview/PackageOverviewDelPayPage';
import PackageOverviewDeliveryDetailsPage from './pages/packages_overview/PackageOverviewDeliveryDetailsPage';
import RegistrationPage from './pages/RegistrationPage';
import ContactPage from './pages/ContactPage';
import BusinessConditionsPage from './pages/BusinessConditionsPage';
import AboutUs from './pages/AboutUs';
import AdminRootPage from './pages/admin/AdminRootPage';
import AdminCustomersPage from './pages/admin/AdminCustomersPage';
import AdminOrdersPage from './pages/admin/AdminOrdersPage';
import AdminProductsPage from './pages/admin/AdminProductsPage';
import AdminSuppliersPage from './pages/admin/AdminSuppliersPage';

export function createRoutes(store) {
  const requireAuthAdmin = (nextState, replace) => {
    /*const { user } = store.getState();
    if (!user) {
      replace('/admin');
    }*/
  };

  return (
    <Route path="/">
      <Route path="/admin">
        <IndexRoute component={AdminLoginPage} />
        <Route component={AdminRootPage} >
          <Route path="customers" component={AdminCustomersPage} onEnter={requireAuthAdmin}/>
          <Route path="orders" component={AdminOrdersPage} onEnter={requireAuthAdmin}/>
          <Route path="products" component={AdminProductsPage} onEnter={requireAuthAdmin}/>
          <Route path="suppliers" component={AdminSuppliersPage} onEnter={requireAuthAdmin}/>
        </Route>
      </Route>
      <Route component={RootPage} >
        <IndexRoute component={HomePage} />
        <Route path="/register" component={RegistrationPage} />
        <Route path="/create_package_beer" component={CreatePackageBeerPage} />
        <Route path="/create_package_message" component={CreatePackageMessagePage} />
        <Route path="/create_package_package" component={CreatePackagePackagePage} />
        <Route path="/create_package_summary" component={CreatePackageSummaryPage} />
        <Route path="/create_package_supplement" component={CreatePackageSupplementPage} />
        <Route path="/package_overview_summary" component={PackageOverviewSummaryPage} />
        <Route path="/package_overview_packages" component={PackageOverviewPackagesPage} />
        <Route path="/package_overview_del_pay" component={PackageOverviewDelPayPage} />
        <Route path="/package_overview_del_details" component={PackageOverviewDeliveryDetailsPage} />
        <Route path="/contact" component={ContactPage} />
        <Route path="/conditions" component={BusinessConditionsPage} />
        <Route path="/about_us" component={AboutUs} />
        <Route path="*" component={PageNotFound} />
      </Route>
    </Route>
  );
}