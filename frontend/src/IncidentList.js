import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class IncidentList extends Component {

    constructor(props) {
        super(props);
        this.state = {incidents: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/incidents')
            .then(response => response.json())
            .then(data => this.setState({incidents: data}));
    }

    async remove(id) {
        await fetch(`/incidents/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedIncidents = [...this.state.incidents].filter(i => i.id !== id);
            this.setState({incidents: updatedIncidents});
        });
    }

    render() {
        const {incidents, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const incidentList = incidents.map(incident => {
            return <tr key={incident.id}>
                <td style={{whiteSpace: 'nowrap'}}>{incident.name}</td>
                <td>{incident.time}</td>
                <td>{incident.address}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/incidents/" + incident.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(incident.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/incidents/new">Report Incident</Button>
                    </div>
                    <h3>Incidents</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="20%">Name</th>
                            <th width="20%">Time</th>
                            <th width="40%">Address</th>
                            <th width="20%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {incidentList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default IncidentList;