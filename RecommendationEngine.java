import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.File;
import java.util.List;

public class RecommendationEngine {

    public static void main(String[] args) {
        try {
            DataModel model = new FileDataModel(new File("data.csv"));
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
            Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

            int userId = 3;                 // change as needed
            int howMany = 3;                // number of recommendations
            List<RecommendedItem> recs = recommender.recommend(userId, howMany);

            System.out.println("📦 Recommendations for User " + userId + ":");
            if (recs.isEmpty()) {
                System.out.println("   (no recommendations found)");
            } else {
                for (RecommendedItem item : recs) {
                    System.out.printf("   Item %d, score %.2f%n",
                                      item.getItemID(), item.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
