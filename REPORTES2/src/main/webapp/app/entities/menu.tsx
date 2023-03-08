import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/venta">
        <Translate contentKey="global.menu.entities.venta" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reporte-historico">
        <Translate contentKey="global.menu.entities.reporteHistorico" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reporte-recurrente">
        <Translate contentKey="global.menu.entities.reporteRecurrente" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
