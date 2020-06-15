import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './projet.reducer';
import { IProjet } from 'app/shared/model/projet.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProjetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Projet extends React.Component<IProjetProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { projetList, match } = this.props;
    return (
      <div>
        <h2 id="projet-heading">
          <Translate contentKey="rctSampleApplicationApp.projet.home.title">Projets</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rctSampleApplicationApp.projet.home.createLabel">Create new Projet</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.projet.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.projet.devise">Devise</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.projet.periodicite">Periodicite</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.projet.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.projet.utilisateur">Utilisateur</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projetList.map((projet, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${projet.id}`} color="link" size="sm">
                      {projet.id}
                    </Button>
                  </td>
                  <td>{projet.nom}</td>
                  <td>{projet.devise}</td>
                  <td>{projet.periodicite}</td>
                  <td>
                    <TextFormat type="date" value={projet.date} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{projet.utilisateur ? <Link to={`utilisateur/${projet.utilisateur.id}`}>{projet.utilisateur.login}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${projet.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projet.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${projet.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ projet }: IRootState) => ({
  projetList: projet.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Projet);
