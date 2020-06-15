import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './champs.reducer';
import { IChamps } from 'app/shared/model/champs.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IChampsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Champs extends React.Component<IChampsProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { champsList, match } = this.props;
    return (
      <div>
        <h2 id="champs-heading">
          <Translate contentKey="rctSampleApplicationApp.champs.home.title">Champs</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp;
            <Translate contentKey="rctSampleApplicationApp.champs.home.createLabel">Create new Champs</Translate>
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
                  <Translate contentKey="rctSampleApplicationApp.champs.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.champs.position">Position</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.champs.longueur">Longueur</Translate>
                </th>
                <th>
                  <Translate contentKey="rctSampleApplicationApp.champs.type">Type</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {champsList.map((champs, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${champs.id}`} color="link" size="sm">
                      {champs.id}
                    </Button>
                  </td>
                  <td>{champs.code}</td>
                  <td>{champs.position}</td>
                  <td>{champs.longueur}</td>
                  <td>{champs.type}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${champs.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${champs.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${champs.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ champs }: IRootState) => ({
  champsList: champs.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Champs);
