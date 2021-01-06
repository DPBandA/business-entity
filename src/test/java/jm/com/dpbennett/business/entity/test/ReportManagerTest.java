/*
Report Manager (RM) - Reporting module. 
Copyright (C) 2021  D P Bennett & Associates Limited

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

Email: info@dpbennett.com.jm
 */
package jm.com.dpbennett.business.entity.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jm.com.dpbennett.business.entity.hrm.Department;
import jm.com.dpbennett.business.entity.hrm.User;
import jm.com.dpbennett.business.entity.sc.ComplianceSurvey;
import jm.com.dpbennett.business.entity.util.BusinessEntityUtils;
import org.junit.Test;

/**
 *
 * @author Desmond Bennett
 */
public class ReportManagerTest {

    @Test
    public void getDepartmentFullCode() {
        HashMap prop = new HashMap();

        prop.put("javax.persistence.jdbc.user",
                "root");
        prop.put("javax.persistence.jdbc.password",
                "bsj0001"); // TK REMOVE PWD WHEN DONE AND DISABLE TESTING.
        prop.put("javax.persistence.jdbc.url",
                "jdbc:mysql://172.16.0.10:3306/jmtstest");
        prop.put("javax.persistence.jdbc.driver",
                "com.mysql.jdbc.Driver");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU", prop);
        EntityManager em = emf.createEntityManager();

        Department dept = Department.findDepartmentByName(em, "Inspectorate - Standards Compliance");
        if (dept != null) {
            List<Object[]> reportData = ComplianceSurvey.getReportRecords(
                    em,
                    BusinessEntityUtils.getDateString(new Date(), "'", "YMD", "-"),
                    BusinessEntityUtils.getDateString(new Date(), "'", "YMD", "-"),
                    dept.getId());

            // Print data data
            for (Object[] rowData : reportData) {

                System.out.println("Job number: " + (String) rowData[0]);
                System.out.println("Consignee: " + (String) rowData[1]);
                System.out.println("Comments: " + (String) rowData[2]);
                System.out.println("Business office: " + (String) rowData[3]);
                System.out.println("Inspector first name: " + (String) rowData[4]);
                System.out.println("Inspector last name: " + (String) rowData[5]);
            }
        } else {
            System.out.println("Department not found!");
        }

    }

}
