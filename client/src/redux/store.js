import * as thunk from "redux-thunk";
import { createStore, applyMiddleware } from "redux";

const initialState = {
  columns: [],
  locations: [],
  selectedColumns: []
};

function reducer(state = initialState, action) {
  switch (action.type) {
    case "SET_COLUMNS": {
      const newSelectedColumns = action.columns.map(column =>
        column.toLowerCase().startsWith("lat") ||
        column.toLowerCase().startsWith("lon") ||
        column.toLowerCase().startsWith("lng")
          ? true
          : false
      );
      return {
        columns: action.columns,
        locations: state.locations,
        selectedColumns: newSelectedColumns
      };
    }
    case "SELECT_COLUMN": {
      const newSelectedColumns = state.selectedColumns.concat();
      newSelectedColumns[action.num] = !newSelectedColumns[action.num];
      return {
        columns: state.columns,
        locations: state.locations,
        selectedColumns: newSelectedColumns
      };
    }
    case "SET_LOCATIONS": {
      const newLocations = action.locations.map(location => {
        const newLocation = { position: {} };
        for (const key in location) {
          if (key.toLowerCase().startsWith("lat")) {
            newLocation.position["lat"] = location[key];
          } else if (
            key.toLowerCase().startsWith("lon") ||
            key.toLowerCase().startsWith("lng")
          ) {
            newLocation.position["lng"] = location[key];
          } else {
            newLocation[key] = location[key];
          }
        }
        return newLocation;
      });
      return {
        columns: state.columns,
        locations: newLocations,
        selectedColumns: state.selectedColumns
      };
    }
    default: {
      return state;
    }
  }
}

const store = createStore(reducer, applyMiddleware(thunk.default));

export default store;
