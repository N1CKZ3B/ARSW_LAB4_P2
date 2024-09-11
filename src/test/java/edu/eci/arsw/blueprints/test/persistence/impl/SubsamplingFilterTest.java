package edu.eci.arsw.blueprints.test.persistence.impl;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubsamplingFilterTest {

    private SubsamplingFilter sFilter;

    private Blueprint blueprint;
    @BeforeEach
    public void setUp() {
        sFilter = new SubsamplingFilter();
        blueprint = new Blueprint("Jhon", "Home");
    }


    @Test
    public void shouldEliminate() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4)
        ));

        Blueprint filteredBlueprint = sFilter.filterBlueprint(blueprint);

        assertEquals(2, filteredBlueprint.getPoints().size());
    }


    @Test
    public void shoudElimOdd() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(7, 7),
                new Point(3, 3),
                new Point(5, 5)
        ));
        Blueprint filteredBlueprint = sFilter.filterBlueprint(blueprint);
        assertEquals(3, filteredBlueprint.getPoints().size());

    }

    @Test
    public void shoudFilterPoints() {
        Set<Blueprint> blueprints = new HashSet<>();
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(2, 2)
        ));
        blueprints.add(blueprint);
        Set<Blueprint> filteredBlueprints = sFilter.filterBlueprints(blueprints);
        assertEquals(1, filteredBlueprints.size());
        Blueprint filteredBlueprint = filteredBlueprints.iterator().next();
        List<Point> filteredPoints = filteredBlueprint.getPoints();
        assertEquals(2, filteredPoints.size());
    }

    @Test
    public void shouldElimPoints() {
        blueprint.setPoints(Arrays.asList(
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(5, 5)
        ));
        Blueprint filteredBlueprint = sFilter.filterBlueprint(blueprint);
        List<Point> filteredPoints = filteredBlueprint.getPoints();
        assertEquals(3, filteredPoints.size());
        assertEquals(1, filteredPoints.get(0).getX());
        assertEquals(3, filteredPoints.get(1).getX());
        assertEquals(5, filteredPoints.get(2).getX());
    }

}