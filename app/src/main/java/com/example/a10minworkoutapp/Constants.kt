package com.example.a10minworkoutapp

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList: ArrayList<ExerciseModel> = arrayListOf()
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
             R.drawable.ic_jumpingjack,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val inclinePushUps = ExerciseModel(
            2,
            "Incline Push-Ups",
             R.drawable.ic_inclinepushups,
            false,
            false
        )
        exerciseList.add(inclinePushUps)

        val kneePushUps = ExerciseModel(
            3,
            "Knee Push-Ups",
             R.drawable.ic_knee,
            false,
            false
        )
        exerciseList.add(kneePushUps)

        val pushUps = ExerciseModel(
            4,
            "Push-Ups",
             R.drawable.ic_pushups,
            false,
            false
        )
        exerciseList.add(pushUps)

        val wideArmPushUps = ExerciseModel(
            5,
            "Wide Arm Push-Ups",
             R.drawable.ic_widearmpushups,
            false,
            false
        )
        exerciseList.add(wideArmPushUps)

        val squats = ExerciseModel(
            6,
            "Squats",
             R.drawable.ic_squats,
            false,
            false
        )
        exerciseList.add(squats)

        val backWardLunge = ExerciseModel(
            7,
            "Backward Lunge",
             R.drawable.ic_backwardlaunge,
            false,
            false
        )
        exerciseList.add(backWardLunge)

        val abdominalCrunches = ExerciseModel(
            8,
            "Abdominal Crunches",
             R.drawable.ic_abdominalcrunches,
            false,
            false
        )
        exerciseList.add(abdominalCrunches)

        val mountainClimber = ExerciseModel(
            9,
            "Mountain Climber",
             R.drawable.ic_mountain,
            false,
            false
        )
        exerciseList.add(mountainClimber)

        val legRaise = ExerciseModel(
            10,
            "Leg Raise",
             R.drawable.ic_legraise,
            false,
            false
        )
        exerciseList.add(legRaise)

        val plank = ExerciseModel(
            11,
            "Plank",
             R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val spineLumberTwistStretch = ExerciseModel(
            12,
            "Spinal Lumbar Twist Stretch Left and Right",
             R.drawable.ic_stretch,
            false,
            false
        )
        exerciseList.add(spineLumberTwistStretch)

        return exerciseList
    }
}