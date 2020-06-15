import * as React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import FontAwesomeIcon from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './utilisateur.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUtilisateurDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class UtilisateurDetail extends React.Component<IUtilisateurDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { utilisateurEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="rctSampleApplicationApp.utilisateur.detail.title">Utilisateur</Translate> [<b>{utilisateurEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nom">
                <Translate contentKey="rctSampleApplicationApp.utilisateur.nom">Nom</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.nom}</dd>
            <dt>
              <span id="mdp">
                <Translate contentKey="rctSampleApplicationApp.utilisateur.mdp">Mdp</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.mdp}</dd>
            <dt>
              <span id="role">
                <Translate contentKey="rctSampleApplicationApp.utilisateur.role">Role</Translate>
              </span>
            </dt>
            <dd>{utilisateurEntity.role}</dd>
          </dl>
          <Button tag={Link} to="/entity/utilisateur" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/utilisateur/${utilisateurEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ utilisateur }: IRootState) => ({
  utilisateurEntity: utilisateur.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UtilisateurDetail);
