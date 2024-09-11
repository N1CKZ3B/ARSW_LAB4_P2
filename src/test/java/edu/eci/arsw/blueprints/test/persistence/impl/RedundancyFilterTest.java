package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.BlueprintFilter;
import edu.eci.arsw.blueprints.filters.RedundancyFilter;
import edu.eci.arsw.blueprints.filters.SubsamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RedundancyFilterTest {

    private RedundancyFilter f;

    private Blueprint blueprint;
    @BeforeEach
    public void setUp() {
        f = new RedundancyFilter();
        blueprint = new Blueprint("Jhon", "Home");

    }

    @Test
    public void shouldFilterCons() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        ));

        Blueprint filteredBlueprint = f.filterBlueprint(blueprint);

        assertEquals(3, filteredBlueprint.getPoints().size());
    }

    @Test
    public void shouldElimCons() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(2, 2),
                new Point(3, 3)
        ));

        Blueprint filteredBlueprint = f.filterBlueprint(blueprint);
        assertEquals(3, filteredBlueprint.getPoints().size());
    }

    @Test
    public void shouldFilterOdd() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(2, 2),
                new Point(3, 3),
                new Point(5, 5)
        ));
        Blueprint filteredBlueprint = f.filterBlueprint(blueprint);
        assertEquals(4, filteredBlueprint.getPoints().size());
    }

    @Test
    public void shouldFilterPts() {
        Set<Blueprint> blueprints = new HashSet<>();
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(2, 2),
                new Point(3, 3)
        ));
        blueprints.add(blueprint);
        Set<Blueprint> filteredBlueprints = f.filterBlueprints(blueprints);
        assertEquals(1, filteredBlueprints.size());
        Blueprint filteredBlueprint = filteredBlueprints.iterator().next();
        List<Point> filteredPoints = filteredBlueprint.getPoints();
        assertEquals(3, filteredPoints.size());
    }
}