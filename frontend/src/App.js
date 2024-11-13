import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import IncidentList from './IncidentList';
import IncidentEdit from "./IncidentEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/incidents' exact={true} component={IncidentList}/>
            <Route path='/incidents/:id' component={IncidentEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;