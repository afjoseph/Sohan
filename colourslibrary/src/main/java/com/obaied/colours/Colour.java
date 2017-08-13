package com.obaied.colours;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The type Colour.
 */
public class Colour extends Color {
    private static Random random = new Random();

    //Color Scheme Enumeration (for color scheme generation)
    public enum ColorScheme {
        ColorSchemeAnalagous, ColorSchemeMonochromatic, ColorSchemeTriad, ColorSchemeComplementary
    }

    public enum ColorDistanceFormula {
        ColorDistanceFormulaCIE76, ColorDistanceFormulaCIE94, ColorDistanceFormulaCIE2000
    }

    // ///////////////////////////////////
    // 4 Color Scheme from Color
    // ///////////////////////////////////

    /**
     * Creates an int[] of 4 Colors that complement the Color.
     *
     * @param type ColorSchemeAnalagous, ColorSchemeMonochromatic,
     *             ColorSchemeTriad, ColorSchemeComplementary
     * @return ArrayList<Integer>
     */
    public static int[] colorSchemeOfType(int color, ColorScheme type) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        switch (type) {
            case ColorSchemeAnalagous:
                return Colour.analagousColors(hsv);
            case ColorSchemeMonochromatic:
                return Colour.monochromaticColors(hsv);
            case ColorSchemeTriad:
                return Colour.triadColors(hsv);
            case ColorSchemeComplementary:
                return Colour.complementaryColors(hsv);
            default:
                return null;
        }
    }

    public static int[] analagousColors(float[] hsv) {
        float[] CA1 = {Colour.addDegrees(hsv[0], 15),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05)};
        float[] CA2 = {Colour.addDegrees(hsv[0], 30),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1)};
        float[] CB1 = {Colour.addDegrees(hsv[0], -15),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05)};
        float[] CB2 = {Colour.addDegrees(hsv[0], -30),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1)};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] monochromaticColors(float[] hsv) {
        float[] CA1 = {hsv[0], (float) (hsv[1]), (float) (hsv[2] / 2)};
        float[] CA2 = {hsv[0], (float) (hsv[1] / 2), (float) (hsv[2] / 3)};
        float[] CB1 = {hsv[0], (float) (hsv[1] / 3), (float) (hsv[2] * 2 / 3)};
        float[] CB2 = {hsv[0], (float) (hsv[1]), (float) (hsv[2] * 4 / 5)};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] triadColors(float[] hsv) {

        float[] CA1 = {Colour.addDegrees(hsv[0], 120), (float) (hsv[1]),
                (float) (hsv[2])};
        float[] CA2 = {Colour.addDegrees(hsv[0], 120),
                (float) (hsv[1] * 7 / 6), (float) (hsv[2] - 0.05)};
        float[] CB1 = {Colour.addDegrees(hsv[0], 240), (float) (hsv[1]),
                (float) (hsv[2])};
        float[] CB2 = {Colour.addDegrees(hsv[0], 240),
                (float) (hsv[1] * 7 / 6), (float) (hsv[2] - 0.05)};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] complementaryColors(float[] hsv) {
        float[] CA1 = {hsv[0], (float) (hsv[1] * 5 / 7), (float) (hsv[2])};
        float[] CA2 = {hsv[0], (float) (hsv[1]), (float) (hsv[2] * 4 / 5)};
        float[] CB1 = {Colour.addDegrees(hsv[0], 180), (float) (hsv[1]),
                (float) (hsv[2])};
        float[] CB2 = {Colour.addDegrees(hsv[0], 180),
                (float) (hsv[1] * 5 / 7), (float) (hsv[2])};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static float addDegrees(float addDeg, float staticDeg) {
        staticDeg += addDeg;
        if (staticDeg > 360) {
            float offset = staticDeg - 360;
            return offset;
        } else if (staticDeg < 0) {
            return -1 * staticDeg;
        } else {
            return staticDeg;
        }
    }

    /**
     * Returns black or white, depending on which color would contrast best with the provided color.
     *
     * @param color (Color)
     * @return int
     */
    public static int blackOrWhiteContrastingColor(int color) {
        int[] rgbaArray = new int[]{Colour.red(color), Colour.green(color), Colour.blue(color)};
        double a = 1 - ((0.00299 * (double) rgbaArray[0]) + (0.00587 * (double) rgbaArray[1]) + (0.00114 * (double) rgbaArray[2]));
        return a < 0.5 ? Colour.BLACK : Colour.WHITE;
    }


    /**
     * This method will create a color instance that is the exact opposite color from another color on the color wheel. The same saturation and brightness are preserved, just the hue is changed.
     *
     * @param color (Color)
     * @return int
     */
    public static int complementaryColor(int color) {
        float[] hsv = new float[3];
        Colour.colorToHSV(color, hsv);
        float newH = Colour.addDegrees(180, hsv[0]);
        hsv[0] = newH;

        return Colour.HSVToColor(hsv);
    }

    // CMYK

    /**
     * Color to cMYK.
     *
     * @param color the color int
     * @return float [ ]
     */
    public static float[] colorToCMYK(int color) {
        float r = Colour.red(color);
        float g = Colour.green(color);
        float b = Colour.blue(color);
        float c = 1 - r / 255;
        float m = 1 - g / 255;
        float y = 1 - b / 255;
        float k = Math.min(1, Math.min(c, Math.min(m, y)));
        if (k == 1) {
            c = 0;
            m = 0;
            y = 0;
        } else {
            c = (c - k) / (1 - k);
            m = (m - k) / (1 - k);
            y = (y - k) / (1 - k);
        }

        return new float[]{c, m, y, k};
    }


    /**
     * CMYK to color.
     *
     * @param cmyk the cmyk array
     * @return color
     */
    public static int CMYKtoColor(float[] cmyk) {
        float c = cmyk[0] * (1 - cmyk[3]) + cmyk[3];
        float m = cmyk[1] * (1 - cmyk[3]) + cmyk[3];
        float y = cmyk[2] * (1 - cmyk[3]) + cmyk[3];
        return Colour.rgb((int) ((1 - c) * 255), (int) ((1 - m) * 255), (int) ((1 - y) * 255));
    }

    /**
     * Color to cIE _ lAB.
     *
     * @param color the color int
     * @return double[]
     */
    public static double[] colorToCIE_LAB(int color) {
        // Convert Color to XYZ format first
        double r = Colour.red(color) / 255.0;
        double g = Colour.green(color) / 255.0;
        double b = Colour.blue(color) / 255.0;

        // Create deltaRGB
        r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.40) : (r / 12.92);
        g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.40) : (g / 12.92);
        b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.40) : (b / 12.92);

        // Create XYZ
        double X = r * 41.24 + g * 35.76 + b * 18.05;
        double Y = r * 21.26 + g * 71.52 + b * 7.22;
        double Z = r * 1.93 + g * 11.92 + b * 95.05;

        // Convert XYZ to L*a*b*
        X = X / 95.047;
        Y = Y / 100.000;
        Z = Z / 108.883;
        X = (X > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(X, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * X + 4 / 29.0;
        Y = (Y > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(Y, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * Y + 4 / 29.0;
        Z = (Z > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(Z, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * Z + 4 / 29.0;
        double CIE_L = 116 * Y - 16;
        double CIE_a = 500 * (X - Y);
        double CIE_b = 200 * (Y - Z);
        return new double[]{CIE_L, CIE_a, CIE_b};
    }

    /**
     * CIE _ lab to color.
     *
     * @param cie_lab the double[]
     * @return color
     */
    public static int CIE_LabToColor(double[] cie_lab) {
        double L = cie_lab[0];
        double A = cie_lab[1];
        double B = cie_lab[2];
        double Y = (L + 16.0) / 116.0;
        double X = A / 500 + Y;
        double Z = Y - B / 200;
        X = (Math.pow(X, 3.0) > 0.008856) ? Math.pow(X, 3.0) : (X - 4 / 29.0) / 7.787;
        Y = (Math.pow(Y, 3.0) > 0.008856) ? Math.pow(Y, 3.0) : (Y - 4 / 29.0) / 7.787;
        Z = (Math.pow(Z, 3.0) > 0.008856) ? Math.pow(Z, 3.0) : (Z - 4 / 29.0) / 7.787;
        X = X * .95047;
        Y = Y * 1.00000;
        Z = Z * 1.08883;

        // Convert XYZ to RGB
        double R = X * 3.2406 + Y * -1.5372 + Z * -0.4986;
        double G = X * -0.9689 + Y * 1.8758 + Z * 0.0415;
        double _B = X * 0.0557 + Y * -0.2040 + Z * 1.0570;
        R = (R > 0.0031308) ? 1.055 * (Math.pow(R, (1 / 2.4))) - 0.055 : R * 12.92;
        G = (G > 0.0031308) ? 1.055 * (Math.pow(G, (1 / 2.4))) - 0.055 : G * 12.92;
        _B = (_B > 0.0031308) ? 1.055 * (Math.pow(_B, (1 / 2.4))) - 0.055 : _B * 12.92;
        return Colour.rgb((int) (R * 255), (int) (G * 255), (int) (_B * 255));
    }

    public static double distanceBetweenColors(int colorA, int colorB) {
        return distanceBetweenColorsWithFormula(colorA, colorB, ColorDistanceFormula.ColorDistanceFormulaCIE94);
    }

    public static double distanceBetweenColorsWithFormula(int colorA, int colorB, ColorDistanceFormula formula) {
        double[] lab1 = Colour.colorToCIE_LAB(colorA);
        double[] lab2 = Colour.colorToCIE_LAB(colorB);
        double L1 = lab1[0];
        double A1 = lab1[1];
        double B1 = lab1[2];
        double L2 = lab2[0];
        double A2 = lab2[1];
        double B2 = lab2[2];

        // CIE76 first
        if (formula == ColorDistanceFormula.ColorDistanceFormulaCIE76) {
            double distance = Math.sqrt(Math.pow((L1 - L2), 2) + Math.pow((A1 - A2), 2) + Math.pow((B1 - B2), 2));
            return distance;
        }

        // More Common Variables
        double kL = 1;
        double kC = 1;
        double kH = 1;
        double k1 = 0.045;
        double k2 = 0.015;
        double deltaL = L1 - L2;
        double C1 = Math.sqrt((A1 * A1) + (B1 * B1));
        double C2 = Math.sqrt((A2 * A2) + (B2 * B2));
        double deltaC = C1 - C2;
        double deltaH = Math.sqrt(Math.pow((A1 - A2), 2.0) + Math.pow((B1 - B2), 2.0) - Math.pow(deltaC, 2.0));
        double sL = 1;
        double sC = 1 + k1 * (Math.sqrt((A1 * A1) + (B1 * B1)));
        double sH = 1 + k2 * (Math.sqrt((A1 * A1) + (B1 * B1)));

        // CIE94
        if (formula == ColorDistanceFormula.ColorDistanceFormulaCIE94) {
            return Math.sqrt(Math.pow((deltaL / (kL * sL)), 2.0) + Math.pow((deltaC / (kC * sC)), 2.0) + Math.pow((deltaH / (kH * sH)), 2.0));
        }

        // CIE2000
        // More variables
        double deltaLPrime = L2 - L1;
        double meanL = (L1 + L2) / 2;
        double meanC = (C1 + C2) / 2;
        double aPrime1 = A1 + A1 / 2 * (1 - Math.sqrt(Math.pow(meanC, 7.0) / (Math.pow(meanC, 7.0) + Math.pow(25.0, 7.0))));
        double aPrime2 = A2 + A2 / 2 * (1 - Math.sqrt(Math.pow(meanC, 7.0) / (Math.pow(meanC, 7.0) + Math.pow(25.0, 7.0))));
        double cPrime1 = Math.sqrt((aPrime1 * aPrime1) + (B1 * B1));
        double cPrime2 = Math.sqrt((aPrime2 * aPrime2) + (B2 * B2));
        double cMeanPrime = (cPrime1 + cPrime2) / 2;
        double deltaCPrime = cPrime1 - cPrime2;
        double hPrime1 = Math.atan2(B1, aPrime1);
        double hPrime2 = Math.atan2(B2, aPrime2);
        hPrime1 = hPrime1 % RAD(360.0);
        hPrime2 = hPrime2 % RAD(360.0);
        double deltahPrime = 0;
        if (Math.abs(hPrime1 - hPrime2) <= RAD(180.0)) {
            deltahPrime = hPrime2 - hPrime1;
        } else {
            deltahPrime = (hPrime2 <= hPrime1) ? hPrime2 - hPrime1 + RAD(360.0) : hPrime2 - hPrime1 - RAD(360.0);
        }
        double deltaHPrime = 2 * Math.sqrt(cPrime1 * cPrime2) * Math.sin(deltahPrime / 2);
        double meanHPrime = (Math.abs(hPrime1 - hPrime2) <= RAD(180.0)) ? (hPrime1 + hPrime2) / 2 : (hPrime1 + hPrime2 + RAD(360.0)) / 2;
        double T = 1 - 0.17 * Math.cos(meanHPrime - RAD(30.0)) + 0.24 * Math.cos(2 * meanHPrime) + 0.32 * Math.cos(3 * meanHPrime + RAD(6.0)) - 0.20 * Math.cos(4 * meanHPrime - RAD(63.0));
        sL = 1 + (0.015 * Math.pow((meanL - 50), 2)) / Math.sqrt(20 + Math.pow((meanL - 50), 2));
        sC = 1 + 0.045 * cMeanPrime;
        sH = 1 + 0.015 * cMeanPrime * T;
        double Rt = -2 * Math.sqrt(Math.pow(cMeanPrime, 7) / (Math.pow(cMeanPrime, 7) + Math.pow(25.0, 7))) * Math.sin(RAD(60.0) * Math.exp(-1 * Math.pow((meanHPrime - RAD(275.0)) / RAD(25.0), 2)));

        // Finally return CIE2000 distance
        return Math.sqrt(Math.pow((deltaLPrime / (kL * sL)), 2) + Math.pow((deltaCPrime / (kC * sC)), 2) + Math.pow((deltaHPrime / (kH * sH)), Rt * (deltaC / (kC * sC)) * (deltaHPrime / (kH * sH))));
    }

    private static double RAD(double degree) {
        return degree * Math.PI / 180;
    }

    // Predefined Colors
    // System Colors
    private static Map<String, Integer> systemColorMap = new HashMap<String, Integer>() {{
        put("infoBlue", Colour.rgb(47, 112, 225));
        put("success", Colour.rgb(83, 215, 106));
        put("warning", Colour.rgb(221, 170, 59));
        put("danger", Colour.rgb(229, 0, 15));
    }};

    public static Map<String, Integer> getSystemColorsMap() {
        return systemColorMap;
    }

    public static int getRandomSystemColor() {
        Object[] values = systemColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int infoBlueColor() {
        return getSystemColorsMap().get("infoBlue");
    }

    public static int successColor() {
        return getSystemColorsMap().get("success");
    }

    public static int warningColor() {
        return getSystemColorsMap().get("warning");
    }

    public static int dangerColor() {
        return getSystemColorsMap().get("danger");
    }

    // Whites
    private static Map<String, Integer> whitesColorMap = new HashMap<String, Integer>() {{
        put("antique", Colour.rgb(250, 235, 215));
        put("oldLace", Colour.rgb(253, 245, 230));
        put("ivory", Colour.rgb(255, 255, 240));
        put("seashell", Colour.rgb(255, 245, 238));
        put("ghostWhite", Colour.rgb(248, 248, 255));
        put("snow", Colour.rgb(255, 250, 250));
        put("linen", Colour.rgb(250, 240, 230));
    }};

    public static Map<String, Integer> getWhitesColorsMap() {
        return whitesColorMap;
    }

    public static int getRandomWhiteColor() {
        Object[] values = whitesColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int antiqueWhiteColor() {
        return getWhitesColorsMap().get("antique");
    }

    public static int oldLaceColor() {
        return getWhitesColorsMap().get("oldLace");
    }

    public static int ivoryColor() {
        return getWhitesColorsMap().get("ivory");
    }

    public static int seashellColor() {
        return getWhitesColorsMap().get("seashell");
    }

    public static int ghostWhiteColor() {
        return getWhitesColorsMap().get("ghostWhite");
    }

    public static int snowColor() {
        return getWhitesColorsMap().get("snow");
    }

    public static int linenColor() {
        return getWhitesColorsMap().get("linen");
    }

    // Grays
    private static Map<String, Integer> graysColorMap = new HashMap<String, Integer>() {{
        put("black25Percent", Colour.rgb(64, 64, 64));
        put("black50Percent", Colour.rgb(128, 128, 128));
        put("black75Percent", Colour.rgb(192, 192, 192));
        put("warmGray", Colour.rgb(133, 117, 112));
        put("coolGray", Colour.rgb(118, 122, 133));
        put("charcoal", Colour.rgb(34, 34, 34));
    }};

    public static Map<String, Integer> getGraysColorMap() {
        return graysColorMap;
    }

    public static int getRandomGrayColor() {
        Object[] values = graysColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int black25PercentColor() {
        return getGraysColorMap().get("black25Percent");
    }

    public static int black50PercentColor() {
        return getGraysColorMap().get("black50Percent");
    }

    public static int black75PercentColor() {
        return getGraysColorMap().get("black75Percent");
    }

    public static int warmGrayColor() {
        return getGraysColorMap().get("warmGray");
    }

    public static int coolGrayColor() {
        return getGraysColorMap().get("coolGray");
    }

    public static int charcoalColor() {
        return getGraysColorMap().get("charcoal");
    }

    // Blues
    private static Map<String, Integer> bluesColorMap = new HashMap<String, Integer>() {{
        put("teal", Colour.rgb(103, 153, 170));
        put("steelBlue", Colour.rgb(103, 153, 170));
        put("robinEgg", Colour.rgb(141, 218, 247));
        put("pastelBlue", Colour.rgb(99, 161, 247));
        put("turquoise", Colour.rgb(112, 219, 219));

        put("skyBlue", Colour.rgb(0, 178, 238));
        put("indigo", Colour.rgb(13, 79, 139));
        put("denim", Colour.rgb(67, 114, 170));
        put("blueberry", Colour.rgb(89, 113, 173));
        put("cornflower", Colour.rgb(100, 149, 237));

        put("babyBlue", Colour.rgb(190, 220, 230));
        put("midnightBlue", Colour.rgb(13, 26, 35));
        put("fadedBlue", Colour.rgb(23, 137, 155));
        put("iceberg", Colour.rgb(200, 213, 219));
        put("wave", Colour.rgb(102, 169, 251));
    }};

    public static Map<String, Integer> getBluesColorMap() {
        return bluesColorMap;
    }

    public static int getRandomBlueColor() {
        Object[] values = bluesColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int tealColor() {
        return getBluesColorMap().get("teal");
    }

    public static int steelBlueColor() {
        return getBluesColorMap().get("steelBlue");
    }

    public static int robinEggColor() {
        return getBluesColorMap().get("robinEgg");
    }

    public static int pastelBlueColor() {
        return getBluesColorMap().get("pastelBlue");
    }

    public static int turquoiseColor() {
        return getBluesColorMap().get("turquoise");
    }

    public static int skyBlueColor() {
        return getBluesColorMap().get("skyBlue");
    }

    public static int indigoColor() {
        return getBluesColorMap().get("indigo");
    }

    public static int denimColor() {
        return getBluesColorMap().get("denim");
    }

    public static int blueberryColor() {
        return getBluesColorMap().get("blueberry");
    }

    public static int cornflowerColor() {
        return getBluesColorMap().get("cornflower");
    }

    public static int babyBlueColor() {
        return getBluesColorMap().get("babyBlue");
    }

    public static int midnightBlueColor() {
        return getBluesColorMap().get("midnightBlue");
    }

    public static int fadedBlueColor() {
        return getBluesColorMap().get("fadedBlue");
    }

    public static int icebergColor() {
        return getBluesColorMap().get("iceberg");
    }

    public static int waveColor() {
        return getBluesColorMap().get("wave");
    }

    // Greens
    private static Map<String, Integer> greensColorMap = new HashMap<String, Integer>() {{
        put("emerald", Colour.rgb(1, 152, 117));
        put("grass", Colour.rgb(99, 214, 74));
        put("pastelGreen", Colour.rgb(126, 242, 124));
        put("seafoam", Colour.rgb(77, 226, 140));
        put("paleGreen", Colour.rgb(176, 226, 172));

        put("cactusGreen", Colour.rgb(99, 111, 87));
        put("chartreuse", Colour.rgb(69, 139, 0));
        put("hollyGreen", Colour.rgb(32, 87, 14));
        put("olive", Colour.rgb(91, 114, 34));
        put("oliveDrab", Colour.rgb(107, 142, 35));

        put("moneyGreen", Colour.rgb(134, 198, 124));
        put("honeydew", Colour.rgb(216, 255, 231));
        put("lime", Colour.rgb(56, 237, 56));
        put("cardTable", Colour.rgb(87, 121, 107));
    }};

    public static Map<String, Integer> getGreensColorMap() {
        return greensColorMap;
    }

    public static int getRandomGreenColor() {
        Object[] values = greensColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int emeraldColor() {
        return getGreensColorMap().get("emerald");
    }

    public static int grassColor() {
        return getGreensColorMap().get("grass");
    }

    public static int pastelGreenColor() {
        return getGreensColorMap().get("pastelGreen");
    }

    public static int seafoamColor() {
        return getGreensColorMap().get("seafoam");
    }

    public static int paleGreenColor() {
        return getGreensColorMap().get("paleGreen");
    }

    public static int cactusGreenColor() {
        return getGreensColorMap().get("cactusGreen");
    }

    public static int chartreuseColor() {
        return getGreensColorMap().get("charreuse");
    }

    public static int hollyGreenColor() {
        return getGreensColorMap().get("hollyGreen");
    }

    public static int oliveColor() {
        return getGreensColorMap().get("olive");
    }

    public static int oliveDrabColor() {
        return getGreensColorMap().get("oliveDrab");
    }

    public static int moneyGreenColor() {
        return getGreensColorMap().get("moneyGreen");
    }

    public static int honeydewColor() {
        return getGreensColorMap().get("honeydew");
    }

    public static int limeColor() {
        return getGreensColorMap().get("lime");
    }

    public static int cardTableColor() {
        return getGreensColorMap().get("cardTable");
    }

    // Reds
    private static Map<String, Integer> redsColorMap = new HashMap<String, Integer>() {{
        put("salmon", Colour.rgb(233, 87, 95));
        put("brickRed", Colour.rgb(151, 27, 16));
        put("easterPink", Colour.rgb(241, 167, 162));
        put("grapefruit", Colour.rgb(228, 31, 54));
        put("pink", Colour.rgb(255, 95, 154));

        put("indianRed", Colour.rgb(205, 92, 92));
        put("strawberry", Colour.rgb(190, 38, 37));
        put("coral", Colour.rgb(240, 128, 128));
        put("maroon", Colour.rgb(80, 4, 28));
        put("watermelon", Colour.rgb(242, 71, 63));

        put("tomato", Colour.rgb(255, 99, 71));
        put("pinkLipstick", Colour.rgb(255, 105, 180));
        put("paleRose", Colour.rgb(255, 228, 225));
        put("crimson", Colour.rgb(187, 18, 36));
    }};

    public static Map<String, Integer> getRedsColorMap() {
        return redsColorMap;
    }

    public static int getRandomRedColor() {
        Object[] values = redsColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int salmonColor() {
        return getRedsColorMap().get("salmon");
    }

    public static int brickRedColor() {
        return getRedsColorMap().get("brickRed");
    }

    public static int easterPinkColor() {
        return getRedsColorMap().get("easterPink");
    }

    public static int grapefruitColor() {
        return getRedsColorMap().get("grapefruit");
    }

    public static int pinkColor() {
        return getRedsColorMap().get("pink");
    }

    public static int indianRedColor() {
        return getRedsColorMap().get("indianRed");
    }

    public static int strawberryColor() {
        return getRedsColorMap().get("strawberry");
    }

    public static int coralColor() {
        return getRedsColorMap().get("coral");
    }

    public static int maroonColor() {
        return getRedsColorMap().get("maroon");
    }

    public static int watermelonColor() {
        return getRedsColorMap().get("watermelon");
    }

    public static int tomatoColor() {
        return getRedsColorMap().get("tomato");
    }

    public static int pinkLipstickColor() {
        return getRedsColorMap().get("pinkLipstick");
    }

    public static int paleRoseColor() {
        return getRedsColorMap().get("paleRose");
    }

    public static int crimsonColor() {
        return getRedsColorMap().get("crimson");
    }

    // Purples
    private static Map<String, Integer> purplesColorMap = new HashMap<String, Integer>() {{
        put("eggplant", Colour.rgb(105, 5, 98));
        put("pastelPurple", Colour.rgb(207, 100, 235));
        put("palePurple", Colour.rgb(229, 180, 235));
        put("coolPurple", Colour.rgb(140, 93, 228));
        put("violet", Colour.rgb(191, 95, 255));

        put("plum", Colour.rgb(139, 102, 139));
        put("lavender", Colour.rgb(204, 153, 204));
        put("raspberry", Colour.rgb(135, 38, 87));
        put("fuschia", Colour.rgb(255, 20, 147));
        put("grape", Colour.rgb(54, 11, 88));

        put("periWinkle", Colour.rgb(135, 159, 237));
        put("orchid", Colour.rgb(218, 112, 214));
    }};

    public static Map<String, Integer> getPurplesColorMap() {
        return purplesColorMap;
    }

    public static int getRandomPurpleColor() {
        Object[] values = purplesColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int eggplantColor() {
        return getPurplesColorMap().get("eggplant");
    }

    public static int pastelPurpleColor() {
        return getPurplesColorMap().get("pastelPurple");
    }

    public static int palePurpleColor() {
        return getPurplesColorMap().get("palePurple");
    }

    public static int coolPurpleColor() {
        return getPurplesColorMap().get("coolPurple");
    }

    public static int violetColor() {
        return getPurplesColorMap().get("violet");
    }

    public static int plumColor() {
        return getPurplesColorMap().get("plum");
    }

    public static int lavenderColor() {
        return getPurplesColorMap().get("lavender");
    }

    public static int raspberryColor() {
        return getPurplesColorMap().get("raspberry");
    }

    public static int fuschiaColor() {
        return getPurplesColorMap().get("fuschia");
    }

    public static int grapeColor() {
        return getPurplesColorMap().get("grape");
    }

    public static int periwinkleColor() {
        return getPurplesColorMap().get("periWinkle");
    }

    public static int orchidColor() {
        return getPurplesColorMap().get("orchid");
    }

    // Yellows
    private static Map<String, Integer> yellowsColorMap = new HashMap<String, Integer>() {{
        put("goldenRod", Colour.rgb(215, 170, 51));
        put("yellowGreen", Colour.rgb(192, 242, 39));
        put("banana", Colour.rgb(229, 227, 58));
        put("mustard", Colour.rgb(205, 171, 45));
        put("buttermilk", Colour.rgb(254, 241, 181));

        put("gold", Colour.rgb(139, 117, 18));
        put("cream", Colour.rgb(240, 226, 187));
        put("lightCream", Colour.rgb(240, 238, 215));
        put("wheat", Colour.rgb(240, 238, 215));
        put("beige", Colour.rgb(245, 245, 220));
    }};

    public static Map<String, Integer> getYellowsColorMap() {
        return yellowsColorMap;
    }

    public static int getRandomYellowColor() {
        Object[] values = yellowsColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int goldenrodColor() {
        return getYellowsColorMap().get("goldenRod");
    }

    public static int yellowGreenColor() {
        return getYellowsColorMap().get("yellowGreen");
    }

    public static int bananaColor() {
        return getYellowsColorMap().get("banana");
    }

    public static int mustardColor() {
        return getYellowsColorMap().get("mustard");
    }

    public static int buttermilkColor() {
        return getYellowsColorMap().get("buttermilk");
    }

    public static int goldColor() {
        return getYellowsColorMap().get("gold");
    }

    public static int creamColor() {
        return getYellowsColorMap().get("cream");
    }

    public static int lightCreamColor() {
        return getYellowsColorMap().get("lightCream");
    }

    public static int wheatColor() {
        return getYellowsColorMap().get("wheat");
    }

    public static int beigeColor() {
        return getYellowsColorMap().get("beige");
    }

    // Oranges
    private static Map<String, Integer> orangesColorMap = new HashMap<String, Integer>() {{
        put("peach", Colour.rgb(242, 187, 97));
        put("burntOrange", Colour.rgb(184, 102, 37));
        put("pastelOrange", Colour.rgb(248, 197, 143));
        put("cantaloupe", Colour.rgb(250, 154, 79));
        put("carrot", Colour.rgb(237, 145, 33));
        put("mandarin", Colour.rgb(247, 145, 55));
    }};

    public static Map<String, Integer> getOrangesColorMap() {
        return orangesColorMap;
    }

    public static int getRandomOrangeColor() {
        Object[] values = orangesColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int peachColor() {
        return getOrangesColorMap().get("peach");
    }

    public static int burntOrangeColor() {
        return getOrangesColorMap().get("burntOrange");
    }

    public static int pastelOrangeColor() {
        return getOrangesColorMap().get("pastelOrange");
    }

    public static int cantaloupeColor() {
        return getOrangesColorMap().get("cantaloupe");
    }

    public static int carrotColor() {
        return getOrangesColorMap().get("carrot");
    }

    public static int mandarinColor() {
        return getOrangesColorMap().get("mandarin");
    }

    // Browns
    private static Map<String, Integer> brownsColorMap = new HashMap<String, Integer>() {{
        put("chiliPowder", Colour.rgb(199, 63, 23));
        put("burntSienna", Colour.rgb(138, 54, 15));
        put("chocolate", Colour.rgb(94, 38, 5));
        put("coffee", Colour.rgb(141, 60, 15));
        put("cinnamon", Colour.rgb(123, 63, 9));

        put("almond", Colour.rgb(196, 142, 72));
        put("eggshell", Colour.rgb(252, 230, 201));
        put("sand", Colour.rgb(222, 182, 151));
        put("mud", Colour.rgb(70, 45, 29));
        put("sienna", Colour.rgb(160, 82, 45));

        put("dust", Colour.rgb(236, 214, 197));
    }};

    public static Map<String, Integer> getBrownsColorMap() {
        return brownsColorMap;
    }

    public static int getRandomBrownColor() {
        Object[] values = brownsColorMap.values().toArray();
        return (int) values[random.nextInt(values.length)];
    }

    public static int chiliPowderColor() {
        return getBrownsColorMap().get("chiliPowder");
    }

    public static int burntSiennaColor() {
        return getBrownsColorMap().get("burntSienna");
    }

    public static int chocolateColor() {
        return getBrownsColorMap().get("chocolate");
    }

    public static int coffeeColor() {
        return getBrownsColorMap().get("coffee");
    }

    public static int cinnamonColor() {
        return getBrownsColorMap().get("cinnamon");
    }

    public static int almondColor() {
        return getBrownsColorMap().get("almond");
    }

    public static int eggshellColor() {
        return getBrownsColorMap().get("eggshell");
    }

    public static int sandColor() {
        return getBrownsColorMap().get("sand");
    }

    public static int mudColor() {
        return getBrownsColorMap().get("mud");
    }

    public static int siennaColor() {
        return getBrownsColorMap().get("sienna");
    }

    public static int dustColor() {
        return getBrownsColorMap().get("dust");
    }

    public static int getRandomNiceColor() {
        List<Object> values = new ArrayList<>();
        values.addAll(whitesColorMap.values());
        values.addAll(graysColorMap.values());
        values.addAll(bluesColorMap.values());
        values.addAll(greensColorMap.values());
        values.addAll(redsColorMap.values());
        values.addAll(purplesColorMap.values());
        values.addAll(yellowsColorMap.values());
        values.addAll(orangesColorMap.values());
        values.addAll(brownsColorMap.values());
        return (int) values.get(random.nextInt(values.size()));
    }

    // All Holo Colors in Android
    public static int holoBlueLightColor() {
        return Colour.parseColor("#ff33b5e5");
    }

    public static int holoGreenLightColor() {
        return Colour.parseColor("#ff99cc00");
    }

    public static int holoRedLightColor() {
        return Colour.parseColor("#ffff4444");
    }

    public static int holoBlueDarkColor() {
        return Colour.parseColor("#ff0099cc");
    }

    public static int holoGreenDarkColor() {
        return Colour.parseColor("#ff669900");
    }

    public static int holoRedDarkColor() {
        return Colour.parseColor("#ffcc0000");
    }

    public static int holoPurpleColor() {
        return Colour.parseColor("#ffaa66cc");
    }

    public static int holoOrangeLightColor() {
        return Colour.parseColor("#ffffbb33");
    }

    public static int holoOrangeDarkColor() {
        return Colour.parseColor("#ffff8800");
    }

    public static int holoBlueBrightColor() {
        return Colour.parseColor("#ff00ddff");
    }

    // Holo Background colors

    public static int backgroundDarkColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int backgroundLightColor() {
        return Colour.parseColor("#ffffffff");
    }

    public static int brightForegroundDarkColor() {
        return Colour.parseColor("#ffffffff");
    }

    public static int brightForegroundLightColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int brightForegroundDarkDisabledColor() {
        return Colour.parseColor("#80ffffff");
    }

    public static int backgroundHoloDarkColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int backgroundHoloLightColor() {
        return Colour.parseColor("#fff3f3f3");
    }

    public static int brightForegroundHoloDarkColor() {
        return Colour.parseColor("#fff3f3f3");
    }

    public static int brightForegroundHoloLightColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int brightForegroundDisabledHoloDarkColor() {
        return Colour.parseColor("#ff4c4c4c");
    }

    public static int brightForegroundDisabledHoloLightColor() {
        return Colour.parseColor("#ffb2b2b2");
    }

    public static int dimForegroundHoloDarkColor() {
        return Colour.parseColor("#bebebe");
    }

    public static int dimForegroundDisabledHoloDarkColor() {
        return Colour.parseColor("#80bebebe");
    }

    public static int hintForegroundHoloDarkColor() {
        return Colour.parseColor("#808080");
    }

    public static int dimForegroundHoloLightColor() {
        return Colour.parseColor("#323232");
    }

    public static int dimForegroundDisabledHoloLightColor() {
        return Colour.parseColor("#80323232");
    }

    public static int hintForegroundHoloLightColor() {
        return Colour.parseColor("#808080");
    }

    public static int highlightedTextHoloDarkColor() {
        return Colour.parseColor("#6633b5e5");
    }

    public static int highlightedTextHoloLightColor() {
        return Colour.parseColor("#ff00ddff");
    }
}
