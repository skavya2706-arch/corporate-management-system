package util;


import dao.TenderDAO;
import model.Tender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TenderReportGenerator {

    public static File generateReport() {

        File reportFile =
                new File("Company_Tender_Report.txt");

        TenderDAO tenderDAO = new TenderDAO();

        ArrayList<Tender> tenders =
                tenderDAO.viewTenders();

        try {

            PrintWriter writer =
                    new PrintWriter(
                            new FileWriter(reportFile)
                    );

            writer.println("==============================================");
            writer.println("          COMPANY TENDER REPORT");
            writer.println("==============================================");
            writer.println();

            if (tenders == null || tenders.isEmpty()) {

                writer.println("No tenders found.");
                writer.println();

            } else {

                writer.println(
                        "Total Tenders : " + tenders.size()
                );

                writer.println();

                for (Tender tender : tenders) {

                    writer.println(
                            "----------------------------------------------"
                    );

                    writer.println(
                            "Tender ID     : "
                                    + tender.getTenderId()
                    );

                    writer.println(
                            "Tender Name   : "
                                    + tender.getTenderName()
                    );

                    writer.println(
                            "Company Name  : "
                                    + tender.getCompanyName()
                    );

                    writer.println(
                            "Tender Type   : "
                                    + tender.getTenderType()
                    );

                    writer.println(
                            "Amount        : "
                                    + tender.getAmount()
                    );

                    writer.println(
                            "Issue Date    : "
                                    + tender.getIssueDate()
                    );

                    writer.println(
                            "Due Date      : "
                                    + tender.getDueDate()
                    );

                    writer.println(
                            "Tender Status : "
                                    + tender.getTenderStatus()
                    );

                    writer.println(
                            "----------------------------------------------"
                    );

                    writer.println();
                }
            }

            writer.println("==============================================");
            writer.println("              END OF REPORT");
            writer.println("==============================================");

            writer.close();

            return reportFile;

        } catch (IOException e) {

            System.out.println(
                    "Error generating tender report."
            );

            e.printStackTrace();

        }

        return null;
    }
}

